# DistributedMandelbrot
Distributed mandelbrot image generator made for Concurrency and distribution subject, ESEI 

# Workflow
First the server divide the image to generate in tasks and locate them on a linked list, then erects a server.
Clients connect to this server and recieve one task. after processing the task it gives it back to the server.
when all the tasks where processed the servers stop accepting clients and write the procesed image to a file.
