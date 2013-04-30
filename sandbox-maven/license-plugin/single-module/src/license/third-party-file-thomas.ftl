<#function licenseFormat licenses>
    <#assign result = ""/>
    <#list licenses as license>
        <#assign result = result + " (" +license + ")"/>
    </#list>
    <#return result>
</#function>
<#function licenseUrl project>

    <#if (project.licenses?size == 0)>
        <#return "[no license url defined]">
    <#else>
        <#assign result = ""/>
        <#list project.licenses as license>
            <#assign  result= result + "["+(license.url!"no url defined")+"]">
        </#list>
        <#return result>
    </#if>
</#function>
<#function artifactFormat p>
    <#if p.name?index_of('Unnamed') &gt; -1>
        <#return p.artifactId + " (" + p.groupId + ":" + p.artifactId + ":" + p.version + " - " + (p.url!"no url defined") + ")">
    <#else>
        <#return p.name + " (" + p.groupId + ":" + p.artifactId + ":" + p.version + " - " + (p.url!"no url defined") + ")">
    </#if>
</#function>

<#if dependencyMap?size == 0>
The project has no dependencies.
<#else>
Lists of ${dependencyMap?size} third-party dependencies.
    <#list dependencyMap as e>
        <#assign project = e.getKey()/>
        <#assign licenses = e.getValue()/>
    ${licenseUrl(project)} ${licenseFormat(licenses)} ${artifactFormat(project)}
    </#list>
</#if>
