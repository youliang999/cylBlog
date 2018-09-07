<#assign defaultAssets={

} />

<#macro MergeAssets type assets=[] >
    <#list assets as file>
        <#if file?length gt 0 && type='css'>
        <link rel="stylesheet" type="text/css" href="${AssetsUrl("??${file}")}"/>
        <#elseif file?length gt 0 && type='js'>
        <script type="text/javascript" src="${AssetsUrl("??${file}")}"></script>
        </#if>
    </#list>
</#macro>
<#function AssetsUrl file="">
<#--用macro宏会导致空格的出现，所以换成function-->
    <#assign assetsDomain = g.domain/>
    <#if file?index_of("/") == 0><#return "${assetsDomain}${file}"/><#else> <#return "${assetsDomain}/${file}"/></#if>
</#function>
<#macro csselement file="">
    <#if file?length gt 0>
    <link rel="stylesheet" type="text/css" href="${AssetsUrl("${file}")}"/>
    </#if>
</#macro>
<#macro jselement file="">
    <#if file?length gt 0>
    <script type="text/javascript" src="${AssetsUrl("${file}")}"></script>
    </#if>
</#macro>
<#assign shouldMergeAssets = false/>
<#macro AssetsImport assets=[] needDefaultAssets=true>
    <#if shouldMergeAssets>
        <#if needDefaultAssets>
            <#--<@csselement file='/css/dajie.css'/>-->
            <#--<@csselement file='/css/dajie.css'/>-->
        </#if>
        <@MergeAssets type="css" assets=assets/>
        <#if needDefaultAssets>
            <#--<@jselement file='/js/dajie.js'/>-->
        </#if>
        <@MergeAssets type='js' assets=assets/>
    <#else>
        <#if needDefaultAssets>
            <#list defaultAssets?keys as file>
                <#if file?ends_with('.js')>
                    <@jselement file='${file}' />
                <#else>
                    <@csselement file='${file}' />
                </#if>
            </#list>
        </#if>
        <#list assets as file>
            <#if !defaultAssets[file]?? && (file?ends_with('.js') || file?ends_with('.css'))>
                <#if file?ends_with('.js')>
                    <@jselement file='${file}' />
                <#else>
                    <@csselement file='${file}' />
                </#if>
            </#if>
        </#list>
    </#if>
</#macro>