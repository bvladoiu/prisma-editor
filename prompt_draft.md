


refactor each component in prisma.editor.component package to be a compoponent type that extends HTMLElement.
for example Expertise flow content extension refactor it to be a type Expertise extends HtmlElement.
implement or overload an appropriate method to wrap the existing dsl code in the file that builds this element.
make sure the root element contains a "markage" attribute or flag attribute 'expertise' then in the styles package refactor the existing style to be a stylesheet +selector + rules, all csssom apis that target the 'expertise' marcage attribute.

go one by one through and apply this formula to every file/component in the ui package.
at the end delete any surplus style/stylesheet/file in the styles package that no longer had an associated component. with the exception of Main. make sure Main aggregates all the resulting stylesheets