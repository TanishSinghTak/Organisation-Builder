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

Only one individual is at level 1 (Owner of the company), however numerous employees can be at level i > 1. Each level i employee reports to a level i-1 employee, who is his or her immediate boss. We can create a chain of employees A', A", A"',... from an employee A, where A works under A', A' works under A", and so on. Each employee in the series A', A", A"',... can be referred to as a boss of A.
