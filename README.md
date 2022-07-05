# SimpleCalculator
Simple Calculator in Android MVVM

A calculator app using LiveData and DataBinding for solving mathematical expressions.

## Screenshorts:

![sample](https://user-images.githubusercontent.com/53432684/177303239-784507a1-3418-4d80-9c60-0b59fb6f129d.gif)

## ExpressionParserUtil Android Library:
> In computer science, the shunting yard algorithm is a method for parsing arithmetical or logical expressions
https://github.com/jfransp/ExpressionParserUtil-Android-Library

## Using MotionLayout make the result more obvious :
> BEFORE equals button click
![motionLayout](https://user-images.githubusercontent.com/53432684/177305036-1bfa9470-bae1-4919-8bbe-800abcb52513.jpg)

> AFTER equals button click
![motionLayout2](https://user-images.githubusercontent.com/53432684/177305494-5daf9580-47e5-4af7-a0c0-1038cd6919cf.jpg)

## String removes excess 0
> IF answer(String) = "2.0", then show "2"
```
            if(model.result!!.indexOf(".") > 0){
                model.result = model.result!!.replace(Regex("0+?$"), "")
                model.result = model.result!!.replace(Regex("[.]$"), "")
            }
```

## Dark themes
![darkThemes](https://user-images.githubusercontent.com/53432684/177310363-24f904ad-3f5b-4acc-be25-46a3a2fe7fe0.jpg)

