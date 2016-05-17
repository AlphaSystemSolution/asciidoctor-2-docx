package com.alphasystem.asciidoctor2docx.util;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

import static com.alphasystem.asciidoctor2docx.ApplicationController.CONF_PATH_VALUE;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author sali
 */
public class ConfigurationUtils {

    private static ConfigurationUtils instance;

    public static synchronized ConfigurationUtils getInstance() {
        if (instance == null) {
            try {
                instance = new ConfigurationUtils();
            } catch (ConfigurationException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return instance;
    }

    private final CompositeConfiguration configuration;

    /**
     * Do not let any one instantiate this class.
     *
     * @throws ConfigurationException
     */
    private ConfigurationUtils() throws ConfigurationException {
        Parameters parameters = new Parameters();
        final File file = get(CONF_PATH_VALUE, "system-defaults.properties").toFile();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<>(
                PropertiesConfiguration.class).configure(parameters.fileBased().setFile(file));

        configuration = new CompositeConfiguration();
        configuration.addConfiguration(new SystemConfiguration());
        configuration.addConfiguration(builder.getConfiguration());
    }

//    public String getTitleStyle(Builder builder) {
//        String defaultTitle = configuration.getString("default.title");
//        String titleKey = format("%s", builder.getSource().getClass().getName());
//        if (isInstanceOf(SectionBuilder.class, builder)) {
//            SectionBuilder sectionBuilder = (SectionBuilder) builder;
//            int level = sectionBuilder.getLevel();
//            titleKey = format("%s.%s", titleKey, level);
//        }
//        titleKey = format("%s.title", titleKey);
//        return configuration.getString(titleKey, defaultTitle);
//    }

    public String getDefaultListStyle() {
        return getString("default.list.style");
    }

    public String getExampleCaption() {
        return configuration.getString("example.caption");
    }

    public String getTableCaption() {
        return configuration.getString("table.caption");
    }

    public String getTableOfContentCaption() {
        return configuration.getString("toc.caption");
    }

    public String getTableStyle(String ts) {
        return isBlank(ts) ? null : configuration.getString(ts);
    }

    public String getString(String key) {
        return configuration.getString(key);
    }
}
