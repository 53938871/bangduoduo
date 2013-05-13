<#if model??>
<input type="text" name="${model.field.name}" class="${model.field.css!''}" value="<#if model.value?? && model.value!=''>${model.value}</#if>"/>
</#if>