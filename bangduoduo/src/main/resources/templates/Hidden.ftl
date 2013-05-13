<#if model??>
<input type="hidden" name="${model.field.name}" value="<#if model.value?? && model.value!=''>${model.value}</#if>"/>
</#if>