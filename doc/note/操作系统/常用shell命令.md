# 常用shell命令

- 查看对方网络端口是否可用
```shell
# mac
nc -vz -w 2 192.168.1.104 3306
```

- 删除指定类型文件
```shell
find ./ -name core.* | xargs rm -rf
# maxdepth限定了查找子目录的深度，name 指出查找文件名
find ./  -maxdepth 1 -name  ' *.md '  -delete
# mac
find ./ -name "*.md" -exec rm -rf {} \;
```

- 文件类型转换
```shell
# 需要安装 pandoc
for file in ./*
do
    if test -f $file; then
        if [ "${file##*.}" = "docx" ]; then 
            pandoc -f docx -t markdown --extract-media ./images -o ${file%.*}.md ${file}
        else
            echo 1
        fi
    fi
done

```