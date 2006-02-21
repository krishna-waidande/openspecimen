<%@ page import="edu.wustl.catissuecore.util.global.Constants"%>
<%@ page import="edu.wustl.catissuecore.actionForm.AdvanceSearchForm"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<head>
    <script language="JavaScript">

    </script>
    <%
    String objectName = request.getParameter("objectName");
   	String action = "ParticipantAdvanceSearch.do?pageOf=pageOfParticipantAdvanceSearch";
	if(objectName != null)
	{
		if(objectName.equals(Constants.PARTICIPANT))
		{
			action = "CollectionProtocolAdvanceSearch.do?pageOf=pageOfCollectionProtocolAdvanceSearch";
		}
		else if(objectName.equals(Constants.COLLECTION_PROTOCOL))
		{
			action = "SpecimenCollectionGroupAdvanceSearch.do?pageOf=pageOfSpecimenCollectionGroupAdvanceSearch";
		}
		else if(objectName.equals(Constants.SPECIMEN_COLLECTION_GROUP))
		{
			action = "SpecimenAdvanceSearch.do?pageOf=pageOfSpecimenAdvanceSearch";
		}
		else if(objectName.equals(Constants.SPECIMEN))
		{
			action = "SpecimenAdvanceSearch.do?pageOf=pageOfSpecimenAdvanceSearch";
		}
	}
	%>
</head>

<html:form action="<%=Constants.ADVANCED_SEARCH_RESULTS_ACTION%>">

<table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="100%" height="100%">
       
    <tr width="100%">
        <td height="5%">
			<table width="100%" height="100%">
			<tr>
				<td width="100%">
		        	<html:errors />
				</td>
			</tr>
			</table>
        </td>
    </tr>
    
     <tr width="100%">
       <td height="65%">
       		<table width="100%" height="100%"> 
			<tr>
				<td width="100%">
				    <iframe name="searchPageFrame" id="searchPageFrame" src="<%=action%>" width="90%" height="100%" frameborder="0" scrolling="auto">
		            </iframe>
				</td>
			</tr>
			</table>
        </td>
     </tr>

    <tr width="100%">
        <td height="25%">
			<table width="100%" height="100%"> 
			<tr>
				<td width="100%">
		            <iframe name="queryFrame" id="queryFrame" src="AdvanceQueryView.do" width="80%" height="100%" frameborder="0" scrolling="auto">
        		    </iframe>
				</td>
			</tr>
			</table>
        </td>
    </tr>
    
    <tr width="100%">
        <td height="5%">
			<table width="100%" height="100%">
			<tr>
				<td width="100%">
		        	<html:submit styleClass="actionButton" >
	        	    	<bean:message  key="buttons.search" />
					</html:submit>
				</td>
			</tr>
			</table>
		</td>
    </tr>
</table>
</html:form>