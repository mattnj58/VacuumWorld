#! /bin/bash/

for i in {1..2000}
do
    java vacworld/VacuumWorld.java -batch -rand $i myw219 | grep SCORE | awk '{print$3}' #> ./myw219/nums.txt 
done
