package com.alphasystem.asciidoctor2docx;

import com.alphasystem.asciidoctor2docx.util.ConfigurationUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.docx4j.wml.Tbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static com.alphasystem.util.nio.NIOFileUtils.USER_DIR;
import static java.lang.String.format;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

/**
 * @author sali
 */
public final class ApplicationController {

    public static final String CONF = "conf";
    public static final Path CONF_PATH = get(System.getProperty("conf.path", USER_DIR), CONF);
    public static final String CONF_PATH_VALUE = CONF_PATH.toString();
    public static final String DEFAULT_TEMPLATE = get(CONF_PATH_VALUE, "default.dotx").toString();
    public static final String STYLES_PATH = get(CONF_PATH_VALUE, "styles.xml").toString();
    private static final ThreadLocal<DocumentContext> CONTEXT = new ThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);
    private static ApplicationController instance;

    public static void startContext(DocumentContext documentContext) {
        CONTEXT.set(documentContext);
    }

    public static DocumentContext getContext() {
        return CONTEXT.get();
    }

    public static void endContext() {
        CONTEXT.remove();
    }

    public static synchronized ApplicationController getInstance() {
        if (instance == null) {
            instance = new ApplicationController();
        }
        return instance;
    }

    protected ConfigurationUtils configurationUtils;
    protected final Invocable engine;

    /**
     * Do not let anyone instantiate this class
     */
    private ApplicationController() {
        configurationUtils = ConfigurationUtils.getInstance();
        engine = (Invocable) initScriptEngine();
    }

    public Object handleScript(String functionName, Object... args) throws ScriptException, NoSuchMethodException {
        return engine.invokeFunction(functionName, args);
    }


    private Tbl getTable(String functionName, Object... args) {
        try {
            return (Tbl) handleScript(functionName, args);
        } catch (Exception e) {
            LOGGER.warn("Unable to get table for \"{}\"", functionName);
        }
        return null;
    }

    private ScriptEngine initScriptEngine() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        Path[] paths = new Path[1];
        paths[0] = get(CONF_PATH_VALUE, "styles.js");
        final String customStyleName = configurationUtils.getString("custom.style.name");
        if (customStyleName != null) {
            paths = ArrayUtils.add(paths, get(CONF_PATH_VALUE, "custom", format("%s.js", customStyleName)));
        }
        for (Path path : paths) {
            try (Reader reader = newBufferedReader(path)) {
                engine.eval(reader);
            } catch (IOException e) {
                throw new UncheckedIOException(e.getMessage(), e);
            } catch (ScriptException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return engine;
    }
}
