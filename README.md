# Selenium with Java and Allure on GitHub

This is a test project to set up Selenium with Java and generate a report using Allure.
The project utilize 
- Page object modal to define different pages
- Github actions to run selenium, generate allure reports and upload to github pages

## Project Setup

1. Install Allure:
```bash
brew install allure
```
2. Run Test:
```bash
mvn clean test
```
3. Generate the Allure report:
```bash
allure serve
```

#You can find the allure results [here](https://alyaothman14.github.io/selenium-java/selenium/)

##Future Improvement
1. Run across multiple browsers
2. Add parallalelization
