<#if model??>
<textarea rows="${model.rows}" class="${model.field.css!''}" cols="${model.cols}" name="${model.field.name}" id="${model.field.id}"><#if model.value !=''>${model.value}</#if></textarea>
</#if>