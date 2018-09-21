<#assign assets=[
<#--"/resource/webuploader-0.1.5/webuploader.min.js",-->
<#--"/resource/js/backend/upload.js",-->
"/resource/webuploader-0.1.5/webuploader.css"
]/>
<@page isBackend=true assets=assets>
    <div class="col-sm-9 col-md-10 bdiv">
    <ol class="breadcrumb header">
        <li><span class="icon glyphicon glyphicon-home"></span>主菜单</li>
        <li>多媒体</li>
        <li class="active">上传附件</li>
    </ol>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="icon icon-upload-alt icon-large"></span>内容</div>
        <div class="panel-body">
            <div id="uploader">
                <div class="upload-list">
                    <div class="placeholder">
                        <div id="picker"></div>
                        <p>或将文件拖到这里，单次最多可选300个</p>
                    </div>
                </div>
                <div class="tip">最大上传文件大小：2MB.</div>
                <div id="status-bar" style="display: none;">
                    <div class="info"></div>
                    <div class="btns">
                        <div id="append-picker"></div>
                        <div class="uploadBtn" onclick="zblog.upload.upload();">开始上传</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript" src="${g.domain}/resource/webuploader-0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="${g.domain}/resource/js/backend/upload.js"></script>
</@page>