## Git 实践笔记

### 使用规范
第一目标是分支线路简单清晰。
- 分支创建要基于功能，而不是个人
- 不是使用 merge，使用 cherry-pick 合并提交
- 不重复推送无意义的提交，合并多个修改后一次性提交

### 使用笔记
**git 合并多个 commit，并提交到远程仓库** 
```shell script
git rebase -i HEAD~2
git push -f
```

**拉取**
```shell script
git fetch -a --prune --tags
git pull --rebase # 更新远程仓库代码使用基变
git merge # 合并代码使用 
```

**遴选某个 commit**
```shell script
# 使用 idea 的 cherry-pick 功能更方便，直接选择需要的 commit 到当前分支就行
git cherry-pick 00b325f875ce1d54d0a605ddac579d106e378e9d
```

**.gitignore 新加规则不生效**
```shell script
# 这一步会删除所有 cached，但是不影响操作，继续执行就行。
git rm -r --cached . 
git add .
git commit -m 'update .gitignore'
```

**修改 commit 的 message**
```shell script
git commit --amend
```

**更新目录下所有 git 仓库**
## 脚本
```shell script
# 脚本
find . -maxdepth 3 -name .git -type d | rev | cut -c 6- | rev | xargs -I {} git -C {} pull

# 解析

## 用 find 搜索目录下全部 .git/ 文件夹
find . -maxdepth 3 -name .git -type d
#. 表示匹配命令执行路径下的全部文件与文件夹
#-maxdepth 3 表示向下搜索最多三层级目录
#-name .git 就是搜索名称为 .git 的内容
#-type d 则指明了我们搜索的范畴：Directories（目录）

#.git 文件夹所在路径
... | rev | cut -c 6- | rev | ...
#rev：首先对搜索到的目录（字符串）进行反转
#cut -c 6-：我们利用 cut 工具将路径进行裁剪，-c 表示删减的是字符（Characters），6- 表示我们删去路径的前 6 个字符（即：.git）
#rev：将处理好的字符串反转回来

#利用 xargs 执行带参数的 git pull
xargs -I {} git -C {} pull

```
## 使用 Makefile 方式
```shell script
# 根目录下，新建 Makefile 文件，写入一下代码，执行 make glall
SHELL:=/bin/bash
.PHONY: all
all: glall

.PHONY: glall
glall:
    find . -maxdepth 3 -name .git -type d | rev | cut -c 6- | rev | xargs -I {} git -C {} pull
```

## 使用自定义命令
```shell script
# 加入到 .zshrc 或者 .bash_profile
echo 'alias gpall="find . -maxdepth 3 -name .git -type d | rev | cut -c 6- | rev | xargs -I {} git -C {} pull"' >> ~/.bash_profile
source ~/.bash_profile
```

### 问题笔记
**问题：** IDEA 下 git checkout 了 .idea/ 下管理模块关系的配置，导致模块依赖不完善，并且 jar 包无法有效加载
- 处理好 gitignore，使 .idea/ 不被 track。


