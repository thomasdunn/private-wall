package privatewall

class ContentController {

    def scaffold = true

    def contentService

    def index() {}

    def list() {
        contentService.getAllContentMetadata().each {
            render """<a href="show/${it.id}"><img src="show/${it.id}" height="300" alt="${it.fileName}" /></a><br /><br />"""
        }
    }

    def show(int id) {
        response.contentType = "image/jpeg"
        response.outputStream << contentService.getFileContent(id)
        response.outputStream.flush()
    }
}
