## Table of Contents

- [Build](#build)
- [Introduction](#introduction)
- [Features](#features)
- [Architecture](#architecture)
- [Demo](#demo)

## Configuration
- Android Studio Giraffe | 2022.3.1 Canary 9

## Introduction
- Demonstration application for Oddle

## Features

List of features that build in this app
* Show all products
* Update discount of product
* Add new product

## Architecture

* Clean architecture with MVVM model
* UI layer -> Data layer[Repository -> [RemoteSource, LocalSource]]
-- UI layer built on Jetpack Compose
-- RemoteSource built on Ktor/Kotlin-serialize + Flow

## Tech-Stack
* Kotlin Coroutine, Flow
* Jetpack Navigation
* Koin
* Kotlin-Serialization
* Ktor

## Demo
![Demo gift](assets/demo.gif)
