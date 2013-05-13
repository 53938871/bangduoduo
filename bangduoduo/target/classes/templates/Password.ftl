<#if model??>
<input type="password" class="${model.field.css!''}" name="${model.field.name}" value="<#if model.value?? && model.value!=''>${model.value}</#if>"/>
</#if>