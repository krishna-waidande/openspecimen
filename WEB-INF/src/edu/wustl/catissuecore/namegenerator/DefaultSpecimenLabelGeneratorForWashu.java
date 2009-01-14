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
 * This  class which contains the default  implementation for Specimen label generation.
 * @author falguni_sachde
 *
 */
public class DefaultSpecimenLabelGeneratorForWashu implements LabelGenerator
{
	/**
	 * Current label 
	 */
	protected Long currentLabel;
	protected Long tmpLabel;
	/**
	 * Datasource Name
	 */

	
	protected long count=0;
	
	String DATASOURCE_JNDI_NAME = "java:/catissuecore";
	/**
	 * Default Constructor
	 */
	public DefaultSpecimenLabelGeneratorForWashu()
	{
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
				currentLabel = getLastAvailableSpecimenLabel(Constants.ORACLE_NUM_TO_STR_FUNCTION_NAME_FOR_LABEL_GENRATION);
				tmpLabel= getLastAvailableSpecimenLabel("identifier");			
			}
			else if (Constants.MSSQLSERVER_DATABASE.equals(Variables.databaseName))
			{
				currentLabel = getLastAvailableSpecimenLabel(Constants.MSSQLSERVER_NUM_TO_STR_FUNCTION_NAME_FOR_LABEL_GENRATION);
				tmpLabel= getLastAvailableSpecimenLabel("identifier");
			}
			else
			{
				currentLabel = getLastAvailableSpecimenLabel(Constants.MYSQL_NUM_TO_STR_FUNCTION_NAME_FOR_LABEL_GENRATION);
				tmpLabel= getLastAvailableSpecimenLabel("identifier");			
			}
	 
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method will retrive unique specimen Lable.
	 * @param databaseConstant
	 * @return noOfRecords
	 */
	private Long getLastAvailableSpecimenLabel(String databaseConstant)  
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
	 * Map forr Tree base specimen entry 
	 */
	Map labelCountTreeMap = new HashMap();
	
	Map tmpLabelCountTreeMap = new HashMap();
	/**
	 * @param parentObject
	 * @param specimenObject
	 */
	synchronized  void setNextAvailableAliquotSpecimenlabel(Specimen parentObject,
				Specimen specimenObject,String prefix) {

		if (Constants.COLLECTION_STATUS_COLLECTED.equals(specimenObject.getCollectionStatus()))
		{
			labelCountTreeMap=setAliquotLabel(parentObject, specimenObject, prefix,labelCountTreeMap);
		}
		else
		{
			tmpLabelCountTreeMap=setAliquotLabel(parentObject, specimenObject, prefix,tmpLabelCountTreeMap);
		}
	}
	private Map setAliquotLabel(Specimen parentObject,
			Specimen specimenObject, String prefix, Map labelTreeMap) {
		String parentSpecimenLabel = (String) parentObject.getLabel();
		long aliquotChildCount = 0;
		
		if(labelTreeMap.containsKey(parentObject.getLabel()))
		{
			 aliquotChildCount= Long.parseLong(labelTreeMap.get(parentObject.getLabel()).toString());	
		}
		else
		{
			// biz logic 
			aliquotChildCount = parentObject.getChildSpecimenCollection().size();	
			
		}
		
	//	specimenObject.setLabel(prefix + parentSpecimenLabel + "_" + (++aliquotChildCount) );
		specimenObject.setLabel(parentSpecimenLabel + "_" + (++aliquotChildCount) );
		labelTreeMap.put(parentObject.getLabel(),aliquotChildCount);	
		
		return labelTreeMap;
	}
	/**
	 * @param parentObject
	 * @param specimenObject
	 */
	synchronized Long setNextAvailableDeriveSpecimenlabel(Specimen parentObject, 
			Specimen specimenObject, Long labelCtr,String prefix) {
	    
		 labelCtr= labelCtr+1;
		 if ((Constants.COLLECTION_STATUS_COLLECTED).equals(specimenObject.getCollectionStatus()))
		{ 
			 specimenObject.setLabel(labelCtr.toString());
		}
		 else 
		 {
			 specimenObject.setLabel(prefix + "_" + labelCtr.toString());
		 }
	   
	    labelCountTreeMap.put(specimenObject,0);
		return labelCtr;
	}
	
	
	
	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.namegenerator.LabelGenerator#setLabel(edu.wustl.common.domain.AbstractDomainObject)
	 */
	public synchronized void setLabel(Object obj) {
		Specimen objSpecimen = (Specimen)obj;
		
		/*if (objSpecimen.getIsCollectionProtocolRequirement())
		{
			return;
		}*/
	   if ((Constants.COLLECTION_STATUS_COLLECTED).equals(objSpecimen.getCollectionStatus()))
		{
		//	currentLabel = generateLabel(objSpecimen,currentLabel,"");
		   tmpLabel = generateLabel(objSpecimen,tmpLabel,"");
		}
		else
		{
			if(objSpecimen.getLabel() == null)
			{
				tmpLabel = generateLabel(objSpecimen,tmpLabel, objSpecimen.getSpecimenType());
			}
		} 

		if(objSpecimen.getChildSpecimenCollection().size()>0)
		{
			Collection specimenCollection = objSpecimen.getChildSpecimenCollection();
			Iterator it = specimenCollection.iterator();
			while(it.hasNext())
			{
				Specimen objChildSpecimen = (Specimen)it.next();
				setLabel(objChildSpecimen);
			}
		}	
				
	}
	
	private Long generateLabel(Specimen objSpecimen, Long labelCtr, String prefix )
	{
       if(!labelCountTreeMap.containsKey(objSpecimen) && objSpecimen.getLineage().equals(Constants.NEW_SPECIMEN))				
		{
    		if(objSpecimen.getLabel()== null)
    	   	{
    	   		labelCtr = labelCtr+1;
    	   		if ((Constants.COLLECTION_STATUS_COLLECTED).equals(objSpecimen.getCollectionStatus()))
    			{
    	   			objSpecimen.setLabel(labelCtr.toString());
    			}
    	   		else {
    	   			objSpecimen.setLabel(prefix + "_" + labelCtr.toString());
    	   		}
    	 	}
    	 
		    labelCountTreeMap.put(objSpecimen.getLabel(),0);
			tmpLabelCountTreeMap.put(objSpecimen.getLabel(),0);
		}
       
       else if(!labelCountTreeMap.containsKey(objSpecimen) && objSpecimen.getLineage().equals(Constants.ALIQUOT))				
		{
			setNextAvailableAliquotSpecimenlabel((Specimen)objSpecimen.getParentSpecimen(),objSpecimen,prefix);
		}
       
	   else if(!labelCountTreeMap.containsKey(objSpecimen) && objSpecimen.getLineage().equals(Constants.DERIVED_SPECIMEN))				
		{
    	  if(objSpecimen.getLabel() == null) {
    		  labelCtr = setNextAvailableDeriveSpecimenlabel((Specimen)objSpecimen.getParentSpecimen(),objSpecimen, labelCtr,prefix);
    	  }
       }
		
		return labelCtr;
	}

	/* (non-Javadoc)
	 * @see edu.wustl.catissuecore.namegenerator.LabelGenerator#setLabel(java.util.List)
	 */
	public synchronized void setLabel(List objSpecimenList) {

		List specimenList = objSpecimenList;
		for (int index=0;index <specimenList.size();index++) 
		{
			Specimen objSpecimen = (Specimen)specimenList.get(index);
			setLabel(objSpecimen);
		}
		
	}
	
	/**
	 * Returns label for the given domain object
	 */
	public String getLabel(Object obj) 
	{
		Specimen objSpecimen = (Specimen)obj;
		setLabel(objSpecimen);
		
		return (objSpecimen.getLabel());
	}	
}