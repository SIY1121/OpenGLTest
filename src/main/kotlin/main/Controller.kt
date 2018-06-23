package main

import com.jogamp.opengl.awt.GLJPanel
import javafx.embed.swing.SwingNode
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import main.event.Listener1
import java.awt.Dimension
import java.net.URL
import java.util.*

class Controller : Initializable {

    @FXML
    lateinit var root: BorderPane
    @FXML
    lateinit var swingNode: SwingNode

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        val panel = GLJPanel()
        panel.addGLEventListener(Listener1())
        swingNode.content = panel
        root.widthProperty().addListener { _, _, n ->
            swingNode.content.size = Dimension(n.toInt(), root.height.toInt())
        }
        root.heightProperty().addListener { _, _, n ->
            swingNode.content.size = Dimension(root.width.toInt(), n.toInt())
        }
    }

}