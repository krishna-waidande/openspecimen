package edu.wustl.catissuecore.namegenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.wustl.catissuecore.domain.Specimen;
import edu.wustl.catissuecore.util.global.Constants;
import edu.wustl.catissuecore.util.global.Variables;
/**
 * This class  contains the default  implementation for Specimen Barcode generation.
 * @author falguni_sachde
 *
 */
public class DefaultSpecimenBarcodeGeneratorForWashu implements BarcodeGenerator
{
	/**
	 * Current Barcode 
	 */
	protected Long currentBarcode;
	/**
	 * Datasource Name
	 */
	String DATASOURCE_JNDI_NAME = "java:/catissuecore";
	/**
	 * Default Constructor
	 */
	public DefaultSpecimenBarcodeGeneratorForWashu()
	{
		super();
		init();
	}
	
	/**
	 * This is a init() function it is called from the default constructor of Base class.When getInstance of base class
	 * called then this init function will be called.
	 * This method will first check the Datatbase Name and then set function name that will convert
	 * lable from int to String
	 */
	protected void init()
	{		
		try
		{
			if(Constants.ORACLE_DATABASE.equals(Variables.databaseName))
			{
				currentBarcode = getLastAvailableSpecimenBarcode(Constants.ORACLE_MAX_BARCODE_COL);
			}
			else if (Constants.MSSQLSERVER_DATABASE.equals(Variables.databaseName))
			{
				currentBarcode = getLastAvailableSpecimenBarcode(Constants.MSSQLSERVER_MAX_BARCODE_COL);
			}
			else
			{
				currentBarcode = getLastAvailableSpecimenBarcode(Constants.MYSQL_MAX_BARCODE_COL);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * This method will retrive unique specimen Barcode.
	 * @param databaseConstant
	 * @return noOfRecords
	 */
	private Long getLastAvailableSpecimenBarcode(String databaseConstant)  
	{
		String sql = "select MAX("+databaseConstant+") from CATISSUE_SPECIMEN";
 		Connection conn = null;
		Long noOfRecords = new Long("0");
        try
		{
        	InitialContext ctx = new InitialContext();
        	DataSource ds = (DataSource)ctx.lookup(DATASOURCE_JNDI_NAME);
        	conn = ds.getConnection();
        	ResultSet resultSet= conn.createStatement().executeQuery(sql);
        	
        	if(resultSet.next())
        	{
        		return new Long (resultSet.getLong(1));
        	}	        
		}
        catch(NamingException e){
        	e.printStackTrace();
        }
        catch(SQLException ex)
        {
        	ex.printStackTrace();
        }
        finally
        {
        	if (conn!=null)
        	{
        		try {
					conn.close();
				} catch (SQLException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
        	}
        }
        return noOfRecords;
	}

	
	
	/**
	 * Map of objects.
	 */
	Map barcodeCountTreeMap = new HashMap();
	
	
	/**
	 * @param parentObject
	 * @param specimenObject
	 */
	 synchronized  void setNextAvailableAliquotSpecimenBarcode(Specimen parentObject,Specimen specimenObject) {
				
		String parentSpecimenBarcode = (String) parentObject.getBarcode();
		long aliquotChildCount = 0;
		
		if(barcodeCountTreeMap.containsKey(parentSpecimenBarcode))
		{
			 aliquotChildCount= Long.parseLong(barcodeCountTreeMap.get(parentSpecimenBarcode).toString());	
		}
		else
		{
			// biz logic 
			aliquotChildCount = parentObject.getChildSpecimenCollection().size();	
			
		}
		
		specimenObject.setBarcode( parentSpecimenBarcode + "_" + (++aliquotChildCount) );
		barcodeCountTreeMap.put(parentSpecimenBarcode,aliquotChildCount);	
		
	}
	/**
	 * @param parentObject
	 * @param specimenObject
	 */
	synchronized void setNextAvailableDeriveSpecimenBarcode(Specimen parentObject, Specimen specimenObject) {
		
		currentBarcode= currentBarcode+1;
		specimenObject.setBarcode(currentBarcode.toString());
		barcodeCountTreeMap.put(specimenObject,0);
	}
	
	
	
	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.namegenerator.BarcodeGenerator#setBarcode(edu.wustl.common.domain.AbstractDomainObject)
	 */
	synchronized public void setBarcode(Object obj) 
	{
		Specimen objSpecimen = (Specimen)obj;
		Specimen parentSpecimen = (Specimen)objSpecimen.getParentSpecimen();
		if(!Constants.COLLECTION_STATUS_COLLECTED.equals(objSpecimen.getCollectionStatus()))
		{
			return;
		}
		if(!barcodeCountTreeMap.containsKey(objSpecimen) &&	objSpecimen.getLineage().equals(Constants.NEW_SPECIMEN))				
		{
			currentBarcode= currentBarcode+1;
			objSpecimen.setBarcode(currentBarcode.toString());
			barcodeCountTreeMap.put(objSpecimen.getBarcode(),0);
		}
		else if(!barcodeCountTreeMap.containsKey(objSpecimen) && objSpecimen.getLineage().equals(Constants.ALIQUOT))				
		{
			
			setNextAvailableAliquotSpecimenBarcode(parentSpecimen,objSpecimen);
		}
	
	
		else if(!barcodeCountTreeMap.containsKey(objSpecimen) && objSpecimen.getLineage().equals(Constants.DERIVED_SPECIMEN))				
		{
			
			setNextAvailableDeriveSpecimenBarcode(parentSpecimen,objSpecimen);
		}
		
		if(objSpecimen.getChildSpecimenCollection().size()>0)
		{
			Collection specimenCollection = objSpecimen.getChildSpecimenCollection();
			Iterator it = specimenCollection.iterator();
			while(it.hasNext())
			{
				Specimen objChildSpecimen = (Specimen)it.next();
				setBarcode(objChildSpecimen);
			}
		}		
		
	}

	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.namegenerator.LabelGenerator#setBarcode(java.util.List)
	 */
	synchronized public void setBarcode(List objSpecimenList) {

		List specimenList = objSpecimenList;
		for (int index=0;index <specimenList.size();index++) 
		{
			Specimen objSpecimen = (Specimen)specimenList.get(index);
			setBarcode(objSpecimen);
		}
		
	}

}