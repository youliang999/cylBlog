<div class="side_widget clearfix">
  <h3 class="headline">近期文章</h3>
    <#if recentBlogs?? && recentBlogs?size gt 0>
      <ul>
          <#list recentBlogs as blog>
              <li>
                  <a href="${g.domain}/blog/${blog.id}">${blog.title}</a>
              </li>
          </#list>
      </ul>
    </#if>
</div>