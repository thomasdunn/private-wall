package privatewall

import spock.lang.*
import grails.plugin.spock.*

class ContentIntegrationSpec extends IntegrationSpec  {

    def "Saving Content to the database"() {

        given: "A new user, post, and content"

        def user = new User(userId: "wwonka", password: "gobstopper")
        def post = new Post(body: "You get nothing!")
        def content = new Content(fileName: "youlose.jpg", fileContent: [ 0, 1, 2 ] as byte[])

        user.addToPosts(post)
        post.addToContents(content)

        when: "The user is saved"

        user.save()

        then: "It is saved successfully and found in the database"

        user.errors.errorCount == 0
        user.userId != null
        User.get(user.id).userId == user.userId
    }
}
