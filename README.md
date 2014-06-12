#<span><img src="http://images.moneysavingexpert.com/images/OrangeLogo.jpg" alt="Orange" width="120" height="120" align="left" hspace="10"/> BookStore - various kinds of books available for you</span>

**Play framework 2**: [![Play framework 2 version](https://badge.fury.io/gh/playframework%2Fplayframework.png)](https://badge.fury.io/gh/playframework%2Fplayframework)
**TokuMX**: [![TokuMX version](https://badge.fury.io/gh/Tokutek%2Fmongo.png)](https://badge.fury.io/gh/Tokutek%2Fmongo) 
**Vaadin**: [![Vaadin version](https://badge.fury.io/gh/vaadin%2Fvaadin.png)](https://badge.fury.io/gh/vaadin%2Fvaadin) 
**Bootstrap**: [![Bootstrap version](https://badge.fury.io/gh/twbs%2Fbootstrap.png)](http://badge.fury.io/gh/twbs%2Fbootstrap)

## Introduction
Nowadays, the cloud application dramatically became very popular due to its main concepts of scalability, configurability, and multi-tenancy. The technology has became a big business and there are many enterprises created in this field in a few years ago. Therefore, the modern application should be chosen good technologies that are compatible with the cloud application. In this project, I have created a **BookStore**, which is a cloud application, for selling books.

## Installation
The installation guide gives you precisely the installation process of the BookStore application. It composes of [Play framework 2](#play-framework-2), [TokuMX](#tokumx), [Vaadin](#dependencies), and [Bootstrap](#dependencies).

### Play framework 2
[Play framework 2](http://www.playframework.com/) is the new kid on the block of Java frameworks for developing web application. It is included many components and APIs needed for modern web application development. The procedure of installation is following:

1. Download the latest version of Play framework 2 from its [official website](http://www.playframework.com/download).
2. Extract and paste it anywhere you want.
3. Open the terminal and type `sudo ln -s path/to/play-2.2.2/play /usr/local/bin/play`, change it to be your own path and your own version.
4. Check that the play command is available by launching the `play help` command in the terminal.

### TokuMX
[TokuMX](http://www.tokutek.com/products/tokumx-for-mongodb/) is an open source built on top of [MongoDB](https://www.mongodb.org/).  It aims to improve the performance of MongoDB by using Fractal tree indexes instead of B-Tree, which is created 40 years ago and still used in MongoDB. TokuMx should be installed replacing MongoDB. Therefore, MongoDB’s fans must migrate their entire databases to TokuMX. The installation instructions are depended on the operation system.

**Linux version:**

1. Download the TukuMX Community Edition from its [official website](http://www.tokutek.com/products/mongodb-download/), choose the right version of your operation system.
2. Extract and paste it into `/usr/local/mongodb`.
3. Create the `/data/db` folder at the home directory.
4. Go to `/usr/local/mongodb/bin` in terminal and Type `mongod` to start the MongoDB's server.
5. Open a new terminal window and Type `mongo` to the start the client.

**Ubuntu, Debian, CentOS, and Fedora version:**

Please follow the [Installation Instructions](http://www.tokutek.com/tokumx-ce-download-packages) on the official website.


**NOTE:** it doesn't have a native window version.

### Dependencies

The dependencies are incredibly easy to be installed. Basically, **Play framework 2** automatically executes a **build.sbt** file and downloads all the required dependencies while running the website. Let's start installing them:

1. Open the terminal and go the your project directory.
2. Run `play run`.

That's it. Now, let's open your favorite browser and access `localhost:9000` to see my beautiful website!