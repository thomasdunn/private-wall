package privatewall

class ContentController {

    def scaffold = true

    def index() {}

    def list() {
        def contents = Content.list()
        contents.each {
            render """<a href="show/${it.id}"><img src="show/${it.id}" height="300" alt="${it.fileName}" /></a><br /><br />"""
        }
    }

    def show(int id) {
        def c = Content.get(id)
        response.contentType = "image/jpeg"
        response.outputStream << c.fileContent
        response.outputStream.flush()
    }
}
