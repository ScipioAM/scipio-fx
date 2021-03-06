package com.github.ScipioAM.scipio_fx.view;

import com.github.ScipioAM.scipio_fx.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.URL;

/**
 * fxml加载器
 *
 * @author Alan Scipio
 * @since 2022/2/22
 */
@Data
@Accessors(chain = true)
public class FXMLViewLoader {

    private final FXMLLoader fxmlLoader;

    public static FXMLViewLoader build() {
        return new FXMLViewLoader();
    }

    private FXMLViewLoader() {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
    }

    /**
     * 加载fxml文件
     *
     * @param location fxml文件的路径
     * @param initArgs 自定义初始化参数
     * @return fxml对象
     * @throws IOException 找不到文件或加载失败
     */
    public FXMLView load(URL location, Object... initArgs) throws IOException {
        fxmlLoader.setLocation(location);
        //加载fxml文件 (controller的onCreate方法被回调)
        Parent rootNode = fxmlLoader.load();
        //获取controller对象
        BaseController controller = fxmlLoader.getController();
        //controller初始化回调
        controller.onLoadInit(rootNode, initArgs);
        return new FXMLView(rootNode, controller, location);
    }

    public FXMLView load(Class<?> clazz, String fxmlPath, Object... initArgs) throws IOException {
        URL location = clazz.getResource(fxmlPath);
        if (location == null) {
            location = new URL(fxmlPath);
        }
        return load(location, initArgs);
    }

    public FXMLView load(Class<?> clazz, String fxmlPath) throws IOException {
        return load(clazz, fxmlPath, (Object[]) null);
    }

}
