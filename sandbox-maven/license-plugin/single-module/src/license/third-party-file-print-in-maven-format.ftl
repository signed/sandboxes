<#if dependencyMap?size == 0>
The project has no dependencies.
<#else>
Lists of ${dependencyMap?size} third-party dependencies.
    <#list dependencyMap as e>
        <#assign project = e.getKey()/>
    <dependency>
        <groupId>${project.groupId}</groupId>
        <artifactId>${project.artifactId}</artifactId>
        <version>${project.version}</version>
    </dependency>
    </#list>
</#if>
