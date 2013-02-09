package privatewall

class PhotosController {

    def contentService

    def index() {
        [ photos : contentService.getAllContentMetadata() ]
    }

    def show(int id) {
        response.contentType = "image/jpeg"
        response.outputStream << contentService.getFileContent(id)
        response.outputStream.flush()
    }

}
