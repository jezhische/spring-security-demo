package com.jezh.springsecurity.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

public class SmartInternalResourceViewResolver extends InternalResourceViewResolver implements InitializingBean {
//    https://www.dropbox.com/s/lo4w2aplsyuk9df/SmartInternalResourceViewResolver.java
//    https://github.com/paulc4/mvc-content-neg-views/issues/1
protected Logger logger = LogManager
        .getLogger(SmartInternalResourceViewResolver.class);

    @Autowired
    protected ServletContext servletContext;

    // Absolute path to the JSP directory
    protected String absDirectoryPath;

    /**
     * Build the requested view, but throw and exception if the JSP file doesn't
     * actually exist. The default {@link InternalResourceViewResolver} doesn't
     * do this.
     *
     * @param viewName
     *            The logical view-name to map to a JSP file of the same name.
     * @return A {@link JstlView} wrapping the JSP. The framework will forward
     *         to this JSP.
     * @throws FileNotFoundException
     *             The requested JSP doesn't exist.
     */
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        logger.info("Attempt to build view: " + viewName);

        // If we haven't see this view before, check to see if it exists
        String absViewPath = absDirectoryPath + viewName + getSuffix();
        File file = new File(absViewPath);

        if (!file.exists()) {
            logger.info(file + " not found.");
            // Save for next time - mark as unknown JSP
            throw new FileNotFoundException();
        }

        logger.info("Found JSP: " + viewName);
        return super.buildView(viewName);
    }

    /**
     * Load a {@link JstlView} corresponding to the specified view for the given
     * locale.
     *
     * @param viewName
     *            The logical view-name to map to a JSP file of the same name.
     * @param locale
     *            The locale of the HTTP client.
     * @return The view (if found) or null otherwise. By returning null this
     *         resolver can be chained.
     */
    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        logger.info("Attempt to load view: " + viewName);

        try {
            return super.loadView(viewName, locale);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Setup the absolute location of this war-file's document root so we can
     * check for the requested JSPs. While we are at it, we validate the JSP
     * location directory specified by {@link #setPrefix(String)}.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // We need the absolute location of the JSP directory so we can
        // check for the JSPs within it. Let's check it's valid whilst
        // we are here.
        String absDir = servletContext.getRealPath(getPrefix());
        logger.info("JSP Directory = " + absDir);
        File dir = new File(absDir);

        if (!dir.exists())
            throw new IllegalArgumentException(absDir + " not found.");

        if (!dir.isDirectory())
            throw new IllegalArgumentException(absDir
                    + " should be a directory.");

        this.absDirectoryPath = absDir.endsWith("/") ? absDir : absDir + '/';
    }
}
