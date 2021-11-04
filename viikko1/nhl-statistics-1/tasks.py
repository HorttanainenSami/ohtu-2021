from invoke import task

@task
def coverage(ctx):
    ctx.run("coverage run --branch -m pytest")

@task(coverage)
def coverage_report(ctx):
    ctx.run("coverage html")

@task(coverage_report)
def report(ctx):
    ctx.run("xdg-open htmlcov/index.html")

from invoke import task

