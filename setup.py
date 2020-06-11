#!/usr/bin/python
# -*- coding:utf-8 -*-

from setuptools import setup

setup(
    name='pyedge',
    version='1.0.1',
    packages=['pyedge'],
    license="MIT",
    author='Leandre',
    author_email="leandre@qq.com",
    url='https://github.com/niulingyun/edge',
    description="encrypt and decrypt keys",
    install_requires=['pycryptodome'],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
)
