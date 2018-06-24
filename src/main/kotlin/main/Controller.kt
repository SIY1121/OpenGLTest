package main

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLJPanel
import javafx.embed.swing.SwingNode
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.layout.BorderPane
import main.event.*
import java.awt.Dimension
import java.net.URL
import java.util.*

class Controller : Initializable {

    @FXML
    lateinit var root: BorderPane
    @FXML
    lateinit var swingNode: SwingNode

    companion object {
        lateinit var panel: GLJPanel
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        panel = GLJPanel(GLCapabilities(GLProfile.get(GLProfile.GL3)))
        panel.addGLEventListener(T14())
        swingNode.content = panel
        root.widthProperty().addListener { _, _, n ->
            swingNode.content.size = Dimension(n.toInt(), root.height.toInt())
        }
        root.heightProperty().addListener { _, _, n ->
            swingNode.content.size = Dimension(root.width.toInt(), n.toInt())
        }
    }

}