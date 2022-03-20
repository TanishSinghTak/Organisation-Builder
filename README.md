# Organisation-Builder
Hierarchy system to store the list of employees of an organisation using a combination of AVL tree and generic tree (N-array tree).
## Introduction
In this, we maintain the list of employees in a company. For each employee, we store many details associated with it in which two of them are most concerned to us:
* ID of the employee (ID is an unique integer for every employee and no two employees can have same ID).
* The level of the employee (Level denotes where the person stands in the hierarchy). Level 1 denotes the highest post in the company (say the owner), level 2 comes below level 1 and so on.

The other details that we store for an employee related to the generic tree:
* **Boss**: Immediate Boss of the employee.
* **Employees** : List of the immediate employees working under the given employee.
* **Empno**: Number of the immediate employees working under the given employee.

The other details that we store for an employee related to the AVL tree:
* **Parent**: Parent of the employee node in AVL tree.
* **Left**: Left child of the employee node in AVL tree.
* **Right**: Right child of the employee node in AVL tree.
* **Height**: height of the employee node in AVL tree.
* **Balance Factor**: Balance Factor of the employee node in AVL tree.

here is only 1 person at level 1, but there can be several employees at level i > 1. Each level i employee works under
a level i-1 employee, which is his/her immediate boss. Given an employee A, we can form a sequence of employees
A',A'', A''', ... where A works under A', A' works under A'', and so on. We say that each employee in the sequence
A',A'', A''',... is a boss of A. You can assume that the employee ids support the ‘<’, ‘>’ and ‘==’ operations over them.
We would like to implement a suitable tree-based data-structure so that we can implement the following operations :
