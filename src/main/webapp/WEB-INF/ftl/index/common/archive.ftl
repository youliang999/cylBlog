<div id="archive" class="side_widget clearfix">
 <h3 class="headline">文章归档</h3>
   <#if categorys?? && categorys?size gt 0>
    <ul>
       <#list categorys as category>
           <li style="display:inline">
               <a href="${g.domain}/category/${category.name!""}/1">${category.name!""}</a>
           </li>
       </#list>
   </ul>
   </#if>
</div>