###############################
### Google Analytics - ui.R ###
###############################

library(shiny) 

shinyUI(pageWithSidebar( 
  
  headerPanel("Google Analytics"), 
  
  sidebarPanel( 
    
    conditionalPanel(
      condition = "input.theTabs != 'hourly'",
      dateRangeInput(inputId = "dateRange",
                     label = "Date range",
                     start = "2013-04-01",
                     max = Sys.Date()
      )
    ),
    conditionalPanel(
      condition = "input.theTabs != 'monthly'",
      sliderInput(inputId = "minimumTime",
                  label = "Hours of interest- minimum",
                  min = 0,
                  max = 23,
                  value = 0,
                  step = 1
      ),
      sliderInput(inputId = "maximumTime",
                  label = "Hours of interest- maximum",
                  min = 0,
                  max = 23,
                  value = 23,
                  step = 1)
    ),
    
    checkboxInput(inputId = "smoother",
                  label = "Add smoother?",
                  value = FALSE),
    
    conditionalPanel(
      condition = "input.smoother == true",
      selectInput("linearModel", "Linear or smoothed",
                  list("lm", "loess"))
    ),
    
    
    checkboxGroupInput(inputId = "domainShow",
                       label = "Show NHS and other domain (defaults to all)?",
                       choices = list("NHS users" = "NHS",
                                      "Other" = "Other")
    ),
    
    radioButtons(inputId = "outputType",
                 label = "Output required",
                 choices = list("Visitors" = "visitors",
                                "Bounce rate" = "bounceRate",
                                "Time on site" = "timeOnSite")),
    uiOutput("reacDomains")
  ),
  mainPanel(
    tabsetPanel(id ="theTabs",
                tabPanel("Summary", textOutput("textDisplay"),
                         value = "summary"),
                tabPanel("Monthly figures",
                         plotOutput("monthGraph"), value = "monthly"),
                tabPanel("Hourly figures",
                         plotOutput("hourGraph"), value = "hourly")
    )
    
  )
))