

# CMS Server File

**Warning: This project is in its initial development phase.**
Expect changes and evolving features. Contributions are welcome, but please understand that the 
project is under active development.

## Description:

This app is a server for a content management system. 
It is a RESTful API that allows users to create, read, update, and delete content. 
The content is stored in a MongoDB database and can be accessed by users with the appropriate permissions.

Its intention is to store content for different Portfolio-websites, and allow users to manage it.
In the future it may also be used to store content for other types of websites.

The implementation of JWT authentication has to be done in the future.

## MongoDB installation:

MongoDB is the database used in this project. It's recommended to use **Mongo DB Compass** to manage the database.
This ServerFileApplication will connect to a local MongoDB database named "serverFileDB" that has to be created manually using compass.
Port, username, and password are set in **application.properties** and will be changed before deployment.

## Endpoints:
### Public Endpoints:
- Get one content: /api/portfolio/{portfolioName}
- Get all projects: /api/portfolio/{portfolioName}/projects
- Get one project: /api/portfolio/{portfolioName}/projects/{projectId}

### Private Endpoints:
- Create portfolio: /api/portfolio/addPortfolio/{portfolioName}/{owner}
- Update portfolio info: /api/portfolio/{portfolioName}/updateInfo
  - Request Body: 
    ```json
    {
      "portfolioName": "New portfolio name",
      "email": "New email"
    }
    ```
- Update Portfolio Title: /api/portfolio/{portfolioName}/updateTitle 
  - Request Body: 
    ```json
    {
      "title": "New title",
      "subtitle": "New subtitle",
      "image": "New image",
      "iconImg": ["URL of icon image 1", "URL of icon image 2"],
      "iconLink": ["URL of icon link 1", "URL of icon link 2"]
    }
    ``` 
- Update Portfolio About: /api/portfolio/{portfolioName}/updateAbout
  - Request Body: 
    ```text
    "your new about text"
    ```
- Add project: /api/portfolio/{portfolioName}/addProject
  - Request Body: 
    ```json
    {
      "projectName": "Your Project Name",
      "projSubtitle": "Your Project Subtitle",
      "projDescription": "Your Project Description",
      "projImages": ["URL of image 1", "URL of image 2"]
    }
    ``` 
- Update project: /api/portfolio/{portfolioName}/updateProject
  - Request Body: 
      ```json
      {
        "projectId": "Your Project Id",
        "projectName": "Your Project Name",
        "projSubtitle": "Your Project Subtitle",
        "projDescription": "Your Project Description",
        "projImages": ["URL of image 1", "URL of image 2"]
      }
      ```
- Delete project: /api/portfolio/{portfolioName}/deleteProject/{projectId}
- Add tag: /api/portfolio/{portfolioName}/addTag
  - Request Body: 
    ```json
    {
      "name": "Your new tag name"
    }
    ``` 
- Update tag: /api/portfolio/{portfolioName}/updateTag
  - Request Body:
    ```json
    {
      "name": "Your new tag name"
    }
    ```  
- Delete tag: /api/portfolio/{portfolioName}/deleteTag/{tagId}
