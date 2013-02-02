
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

String username = "<username>@gmail.com"
String password = "<password>"

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

        // Hex version - useful for linking to Gmail
        //System.out.println(Long.toHexString(gm.getMsgId()))

        // println gm.getThrId()

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
    Multipart multipart = (Multipart) message.getContent()
    // System.out.println(multipart.getCount())

    for (int i = 0; i < multipart.getCount(); i++) {
        BodyPart bodyPart = multipart.getBodyPart(i)
        if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
            continue // dealing with attachments only
        }
        InputStream is = bodyPart.getInputStream()

        f = new File  (System.getProperty("java.io.tmpdir") + bodyPart.getFileName()).withDataOutputStream {
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