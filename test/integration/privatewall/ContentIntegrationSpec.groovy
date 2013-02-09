package privatewall

import spock.lang.*
import grails.plugin.spock.*

class ContentIntegrationSpec extends IntegrationSpec  {

    def contentService

//    def "Saving a user -> post -> content to the database"() {
//
//        given: "A new user, post, and content"
//
//        def user = new User(userId: "wwonka", password: "gobstopper")
//        def post = new Post(body: "You get nothing!")
//        def content = new Content(fileName: "youlose.jpg", fileContent: [ 0, 1, 2 ] as byte[])
//
//        user.addToPosts(post)
//        post.addToContents(content)
//
//        when: "The user is saved"
//
//        user.save()
//
//        then: "Content is found in the database"
//
//        user.errors.errorCount == 0
//        user.userId != null
//        User.get(user.id).userId == user.userId
//
//        post.errors.errorCount == 0
//        post.body != null
//        Post.get(post.id).body == post.body
//
//        content.errors.errorCount == 0
//        content.fileName != null
//        content.fileContent != null
//        content.fileContent.length == 3
//        Content.get(content.id).fileName == content.fileName
//        Content.get(content.id).fileContent == content.fileContent
//    }

    def "Saving a user -> content to the database"() {

        given: "A new user and content"

        def user = new User(userId: "wwonka", password: "gobstopper")
        def content = new Content(fileName: "youlose.jpg", fileContent: [ 0, 1, 2 ] as byte[])

        user.addToContents(content)

        when: "The user is saved"

        user.save()

        then: "Content is found in the database"

        user.errors.errorCount == 0
        user.userId != null
        User.get(user.id).userId == user.userId

        content.errors.errorCount == 0
        content.fileName != null
        content.fileContent != null
        content.fileContent.length == 3
        Content.get(content.id).fileName == content.fileName
        Content.get(content.id).fileContent == content.fileContent
    }

    def "getAllContentMetadata returns data"() {

        given: "Create a user and content"

        def initialCount = Content.getCount()

        def user = new User(userId: "wwonka", password: "gobstopper")
        def content = new Content(fileName: "youlose.jpg", fileContent: [ 0, 1, 2 ] as byte[])

        user.addToContents(content)
        user.save()

        when: "get content metadata"

        def contents = contentService.getAllContentMetadata()

        then: "Check for proper content metadata"

        contents.size() == initialCount + 1
        contents[0].id == content.id
        contents[0].fileName == "youlose.jpg"

    }

}
