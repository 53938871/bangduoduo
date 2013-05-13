<#if model??>
	<#if property?? && property.options??>
	   <#list property.options?keys as option>
	   <input type="radio" name="${model.field.name}" class="${model.field.css!''}" value="${option}" <#if model.value!='' && model.value==option>checked="checked"</#if>>${option}  
	   </#list>	
	</#if>
</#if>