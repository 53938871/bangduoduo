<#if model??>
<select name="${model.field.name}" class="${model.field.css!''}">
	<#if property?? && property.options??>
	   <#list property.options?keys as option>
	   <option value="${option}" <#if model.value==option>selected="selected"</#if>>${property.options[option]}</option>
	   </#list>	
	</#if>
</select>
</#if>