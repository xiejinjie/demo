package main

import (
	"context"
	"fmt"
	"io"
	"log"
	"os"

	"github.com/docker/docker/api/types"
	"github.com/docker/docker/api/types/container"
	"github.com/docker/docker/client"
	"github.com/docker/go-connections/nat"
)

/*
	API文档:https://pkg.go.dev/github.com/docker/docker/client
*/

func main() {
	cli, err := client.NewClientWithOpts(client.FromEnv)
	if err != nil {
		panic(err)
	}

	ImagePull(cli)
	containerId := ContainerCreate(cli)
	ContainerStart(cli, containerId)
	MonitorContainers(cli, containerId)

	// ContainerStop(cli)
	// ContainerRemove(cli)
	// ImageRemove(cli)

	fmt.Println("Image info:")
	listImages(cli)
	fmt.Println("Container info:")
	listContainers(cli)
}

func listImages(cli *client.Client) {
	images, err := cli.ImageList(context.Background(), types.ImageListOptions{})
	if err != nil {
		panic(err)
	}
	for _, image := range images {
		fmt.Printf("%s %s \n\n", image.ID[:19], image.RepoTags)
	}
}

func ImagePull(cli *client.Client) {
	dockerImage := "docker/getting-started"
	reader, err := cli.ImagePull(context.Background(), dockerImage, types.ImagePullOptions{})
	if err != nil {
		panic(err)
	}
	io.Copy(os.Stdout, reader)
}

func ImageRemove(cli *client.Client) {
	imageID := "cb90f98fd791"
	items, err := cli.ImageRemove(context.Background(), imageID, types.ImageRemoveOptions{})
	if err != nil {
		panic(err)
	}
	for _, item := range items {
		fmt.Printf("%s %s\n\n", item.Deleted, item.Untagged)
	}
}

func listContainers(cli *client.Client) {
	containers, err := cli.ContainerList(context.Background(), types.ContainerListOptions{})
	if err != nil {
		panic(err)
	}

	for _, container := range containers {
		fmt.Printf("%s %s\n\n", container.ID[:10], container.Image)
	}
}

func ContainerStart(cli *client.Client, cId string) {
	err := cli.ContainerStart(context.Background(), cId, types.ContainerStartOptions{})
	if err != nil {
		panic(err)
	}
}

func ContainerStop(cli *client.Client) {
	containerName := "docker-demo"
	err := cli.ContainerStop(context.Background(), containerName, nil)
	if err != nil {
		panic(err)
	}
}

func ContainerCreate(cli *client.Client) string {
	containerName := "docker-demo"
	containerPort, _ := nat.NewPort("tcp", "80")
	hostBinding := nat.PortBinding{
		HostIP:   "0.0.0.0",
		HostPort: "80",
	}
	cont, err := cli.ContainerCreate(context.Background(),

		&container.Config{
			Image: "docker/getting-started",
		},
		&container.HostConfig{
			PortBindings: nat.PortMap{
				containerPort: []nat.PortBinding{
					hostBinding,
				},
			},
		},
		nil, nil, containerName)
	if err != nil {
		panic(err)
	}
	fmt.Printf("containerId = %s \n\n", cont.ID)
	return cont.ID
}

func ContainerRemove(cli *client.Client) {
	containerName := "docker-demo"
	err := cli.ContainerRemove(context.Background(), containerName, types.ContainerRemoveOptions{})
	if err != nil {
		panic(err)
	}
}

func MonitorContainers(cli *client.Client, containerID string) {
	statusCh, errCh := cli.ContainerWait(
		context.Background(),
		containerID,
		container.WaitConditionNotRunning,
	)

	select {
	case err := <-errCh:
		if err != nil {
			log.Fatal(err)
		}
	case status := <-statusCh:
		fmt.Printf("status code = %d\n", status.StatusCode)
	}
}
