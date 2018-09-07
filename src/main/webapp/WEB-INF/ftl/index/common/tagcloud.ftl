<div class="side_widget clearfix">
   <h3 class="headline">标签</h3>
    <div id="tagcloud">
    <#if tagVOs?? && tagVOs?size gt 0>
       <#list tagVOs as tagVO>
           <a  href="${g.domain}/tag/${tagVO.tag.name}/1" style="font-size: ${tagVO.fontSize!5}pt;">${tagVO.tag.name}</a>
       </#list>
    </#if>
   </div>
</div>