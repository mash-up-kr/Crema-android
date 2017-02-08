# Crema-android

## Commit - Push 

gitflow를 따르기로 한다.


## XML 네이밍 규칙

```
- id 규칙 : (component 약자)_(화면 이름)_(그 컴포넌트의 성질)

et_sign_in_username


- 레이아웃 규칙 : (화면이름)_(성질)

search_main --> activity 
search_item --> recycler view item
search_item_header --> recycler view header


- string 규칙 : (화면이름)_(성질)


sign_in_hint_username
sign_in_btn_login


- dimen 규칙 : (화면이름)_(성질)_(컴포넌트)

sign_in_margin_horizontal
sign_in_margin_username_password
sign_in_padding_username
```

## Dependency Injector

ButterKnife 를 사용하기로 한다.


## Component Global Variable 네이밍 규칙

(Component 약자)(컴포넌트 성질)

etUsername
btnPost
rvSearch
...
