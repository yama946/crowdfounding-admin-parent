1、数据库建表三范式；# 项目笔记
           
           在mysql中主键是强制设置的，主要用来保证表结构的完整性和记录的唯一性。
           验证主键的必要性。
           ## 一、三大范式
           
           * 第一范式
           
           字段的原子性，这个原子性是字段不能再分，也就是你的业务实现中
           不会产生对字段中更细粒度的查询。
           比如地址，地址中包含省、市等信息，如果保证不会对用户单独统计其所在省
           我们就可以将省市设置为一个字段来创建表。
           
           
           我们使用的框架内部使用的日志系统，不内置日志文件，如果与我们使用的日志系统不同可能内部日志打印不出来。
           
           DispatcherServlet中存在一个大的try...catch......用来进行异常映射的实现。