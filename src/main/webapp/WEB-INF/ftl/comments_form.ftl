<fieldset id="respond" class="comment_form_wrapper">
    <#if !g.allowComment || blog.cstatus =='close'|| (!sf.isUser && blog.pstatus== 0 ) >
            当前禁止评论
        <#else >
            <div id="cancel_comment_reply">
                <a href="#respond" rel="nofollow">点击这里取消回复。</a>
            </div>
            <!--  <form method="post" action="/comments" id="comment_form"> -->
            <input type="hidden" id="postid" name="postid" value="${blog.id}" />
            <input type="hidden" id="comment_parent" name="parent" />
            <#if !comment_author?? || comment_author?length == 0>
                <div id="guest_info">
                    <div id="guest_name">
                        <label for="author"><span>昵称</span>( 必须 )</label>
                        <input id="author" autocomplete="off" type="text" aria-required="true" size="22" name="creator">
                        <div style="display: block;width: 700px; heigt:16px;">
                            <div id="author_err" style="display: none"></div>
                        </div>
                    </div>
                    <div id="guest_email">
                        <label for="email"><span>E-MAIL</span>( 必须 ) - 不会公开 -</label>
                        <input id="email" autocomplete="off" type="text" aria-required="true" size="22" name="email">
                        <div style="display: block;width: 700px; heigt:16px;">
                            <div id="email_err" style="display: none"></div>
                        </div>
                    </div>
                    <div id="guest_url">
                        <label for="url"><span>网址</span></label>
                        <input id="url" type="text" tabindex="3" size="22" placeholder="http://" name="url">
                        <div style="display: block;width: 700px; heigt:16px;">
                            <div id="url_err" style="display: none"></div>
                        </div>
                    </div>
                </div>
            </#if>
            <div id="comment_textarea">
                <div style="display: block;width: 700px; heigt:16px;">
                    <div id="content_err" style="display: none"></div>
                </div>
                <textarea id="comment" tabindex="4" rows="10" cols="50" name="content"></textarea>
            </div>
            <#if comment_author?? && comment_author?length gt 0>
                <div id="guest_info">登录身份:${comment_author!""}</div>
            </#if>
            <div id="submit_comment_wrapper">
                <input id="submit_comment" type="button" value="发表评论" >
            </div>
            <!--   </form> -->
        </#if>
    </>
</fieldset>
<script src="${g.domain}/resource/js/zblog.js"></script>
