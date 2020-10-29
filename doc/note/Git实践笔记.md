## Git 实践笔记

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
git cherry-pick 00b325f875ce1d54d0a605ddac579d106e378e9d
```
**.gitignore 新加规则不生效**
```shell script
git rm -r --cached . # 这一步会删除所有 cached，但是不影响操作，继续执行就行。
git add .
git commit -m 'update .gitignore'
```

### 问题笔记
**问题：** IDEA 下 git checkout 了 .idea/ 下管理模块关系的配置，导致模块依赖不完善，并且 jar 包无法有效加载
- 处理好 gitignore，使 .idea/ 不被 track。


