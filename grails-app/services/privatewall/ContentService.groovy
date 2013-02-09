package privatewall

import org.codehaus.groovy.grails.web.multipart.ContentLengthAwareCommonsMultipartResolver

class ContentService {

    def getAllContentMetadata() {
//        Content.withCriteria {
//            projections {
//                property('id')
//                property('fileName')
//            }
//        }
        Content.list()
    }

    byte[] getFileContent(int id) {
        def c = Content.get(id)
        c.fileContent
    }
}
