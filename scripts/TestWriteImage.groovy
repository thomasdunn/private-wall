import com.sun.mail.gimap.GmailFolder
import com.sun.mail.gimap.GmailMessage
import com.sun.mail.gimap.GmailSSLStore
import privatewall.Content
import privatewall.User

import javax.mail.*

Properties props = System.getProperties()
props.setProperty("mail.store.protocol", "gimaps")

GmailSSLStore store = null
GmailFolder folder = null

String username = System.getProperty("email")
String password = System.getProperty("password")

try {
    Session session = Session.getDefaultInstance(props, null)
    store = session.getStore("gimaps") as GmailSSLStore
    store.connect(username, password)
    folder = (GmailFolder) store.getFolder("INBOX")
    folder.open(Folder.READ_ONLY)
    Message[] ms = folder.getMessages()
    FetchProfile fp = new FetchProfile()
    fp.add(GmailFolder.FetchProfileItem.MSGID)
    fp.add(GmailFolder.FetchProfileItem.THRID)
    fp.add(GmailFolder.FetchProfileItem.LABELS)

    folder.fetch(ms, fp)

    GmailMessage gm
    String[] labels

    for (m in ms) {
        gm = (GmailMessage) m
        println gm.getMsgId() + " " + m.subject

        println writeAttachments(gm)

        labels = gm.getLabels()
        if (labels != null) {
            for (String label : labels) {
                if (label != null) {
                    println "Label: " + label
                }
            }
        }
    }
} catch (MessagingException ex) {
    println "MessageException caught: $ex.toString()"
}
finally {
    if (folder != null && folder.isOpen()) {
        folder.close(true)
    }
    if (store != null) {
        store.close()
    }
}

def writeAttachments(Message message) {
    def names = []
    Multipart multipart

    assert message != null

    if (! message.getContent() instanceof Multipart)
        return []

    multipart = message.getContent()

    for (int i = 0; i < multipart.getCount(); i++) {
        BodyPart bodyPart = multipart.getBodyPart(i)

        if(! Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) ||
                ! bodyPart.type.toLowerCase().startsWith("image")) {
            continue // dealing with image attachments only
        }

        InputStream is = bodyPart.getInputStream()
        def fromUser = User.findByUserId(message.from[0]?.getAddress())

        Content imageContent = new Content()
        imageContent.fileName = message.subject
        imageContent.fileContent = is.getBytes()

        fromUser.addToContents(imageContent)
        fromUser.save()

        names << bodyPart.getFileName()
    }

    names
}