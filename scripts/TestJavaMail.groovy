
/**
 * Created with IntelliJ IDEA.
 * User: tdunn
 * Date: 2/2/13
 * Time: 3:30 AM
 * To change this template use File | Settings | File Templates.
 */

import com.sun.mail.gimap.GmailFolder
import com.sun.mail.gimap.GmailMessage
import com.sun.mail.gimap.GmailSSLStore

import javax.mail.*

Properties props = System.getProperties()
props.setProperty("mail.store.protocol", "gimaps")
//props.setProperty("mail.debug", "true")

GmailSSLStore store = null
GmailFolder folder = null

String username = System.getProperty("email")
String password = System.getProperty("password")

try {
    Session session = Session.getDefaultInstance(props, null)
    store = session.getStore("gimaps") as GmailSSLStore
    store.connect(username, password) //Constants.getUsername(), Constants.getPassword())
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

        def fileName = bodyPart.getFileName()
        f = new File  (System.getProperty("java.io.tmpdir") + fileName).withDataOutputStream {
            byte[] buf = new byte[4096]
            int bytesRead
            while((bytesRead = is.read(buf))!=-1) {
                it.write(buf, 0, bytesRead)
            }
            names << bodyPart.getFileName()
        }
    }

    names
}