package chad.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class StartAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        MainDialog dialog = new MainDialog();
        dialog.setSize(150,290);
        dialog.setVisible(true);
        dialog.requestFocusInWindow();
    }
}
