shinyServer(function(input, output, session) {
  # Store in a convenience variable
  cdata <- session$clientData
  
  # Values from cdata returned as text
  output$clientdataText <- renderText({
    cnames <- names(cdata)
    
    allvalues <- lapply(cnames, function(name) {
      paste(name, cdata[[name]], sep=" = ")
    })
    paste(allvalues, collapse = "\n")
  })
  
  # A histogram
  output$myplot <- renderPlot({
    hist(rnorm(input$obs), main="Generated in renderPlot()")
  })
})
