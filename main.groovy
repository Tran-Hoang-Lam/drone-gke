import groovy.json.JsonSlurper

static main(args) {
    def serviceAccountFile = 'service_account.json'

    GroovyShell shell = new GroovyShell()
    def plugin = shell.parse(new File('./plugin.groovy'))
    def shellHelper = shell.parse(new File('./shell.groovy'))

    println "Starting decode gce token"

    def gceTokenJson = new String(System.getenv("PLUGIN_GCE_TOKEN").decodeBase64())

    def jsonSlurper = new JsonSlurper()

    println "Parsing gce json"
    def gceTokenValue = jsonSlurper.parseText(gceTokenJson)

    def serviceAccountJson = new File(serviceAccountFile)

    println "Save json token to file $serviceAccountFile"
    serviceAccountJson.write(gceTokenJson)

    def environmentParam = shellHelper.getEnvironmentVaribale()

    plugin.authorizeToGke(serviceAccountFile, gceTokenValue.client_email, gceTokenValue.project_id)
    plugin.setCluster(environmentParam.CLUSTER, environmentParam.ZONE)

    String templatePath = environmentParam.TEMPLATE

    new File(templatePath).write(plugin.processTemplate(templatePath, environmentParam))

    plugin.applyTemplate(templatePath)
}


