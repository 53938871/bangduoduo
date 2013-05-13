<#if model??>
	<#if property?? && property.options??>
	   <#list property.options?keys as option>
	   <input type="checkbox" class="${model.field.css!''}" name="${property.options[option]}" value="${option}" <#if model.value!='' && model.value==option>checked="checked"</#if>>${option}  
	   </#list>	
	</#if>
</#if>