<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="theme-color" content="#000000"/>
    <title>Version</title>
</head>
<body>
<div style="text-align: center">
    <h1>
        Loop Environment: ${environmentName}
    </h1>
    <h3>
        Version: ${applicationVersion}
    </h3>
</div>
<div style="text-align: center">
    <#list hostList as hostIp>
        <h3>Server: ${hostIp}</h3>
        <div>
            <iframe src="http://${hostIp}:2812" title="${hostIp}" style="width: 80%; height: 500px; overflow:auto"></iframe>
        </div>
    </#list>
</div>
</body>
</html>
